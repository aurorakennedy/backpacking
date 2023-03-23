import React, { useEffect, useRef, useState } from "react";
import NavBar from "./NavBar";
import "./profilePageStyle.css";
import { LoggedInUser, User } from "./types";
import ItineraryListBox from "./ItineraryListBox";
import httpRequests from "./httpRequests";

type ProfilePageProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
};

const ProfilePage = ({ setLoggedInUser, loggedInUser }: ProfilePageProps) => {
    const [editProfilePictureOpen, seteditProfilePictureOpen] = useState(false);

    const openEditProfilePicture = () => {
        seteditProfilePictureOpen(true);
    };

    const closeEditProfilePicture = () => {
        seteditProfilePictureOpen(false);
    };

    const inputRef = useRef<HTMLInputElement>(null);

    const [selectedImage, setSelectedImage] = useState<File | null>(null);

    const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            setSelectedImage(event.target.files[0]);
        }
    };

    function isJpeg(file: File): boolean {
        return file.type === "image/jpeg";
    }

    async function setProfilePicture() {
        try {
            const imagePromise: Promise<Uint8Array> = httpRequests.getProfilepicture(
                loggedInUser.username
            );
            imagePromise.then((imageArray: Uint8Array) => {
                const imgElement: HTMLImageElement = document.getElementById(
                    "profilePicture"
                ) as HTMLImageElement;
                if (imageArray.length != 0) {
                    const byteArray: Uint8Array = new Uint8Array(imageArray);
                    const blob: Blob = new Blob([byteArray]);
                    const url: string = URL.createObjectURL(blob);
                    imgElement.src = url;
                    console.log(byteArray);
                } else {
                    imgElement.src = "profilePicturePlaceholder.jpeg";
                }
            });
        } catch (error) {
            console.error(error);
        }
    }

    async function submitImageProfilePictureChange(): Promise<
        React.MouseEventHandler<HTMLButtonElement> | any
    > {
        if (selectedImage !== null && !isJpeg(selectedImage)) {
            alert(
                `The uploaded image must be a JPEG file, and have one of 
                the following file extensions: .jpeg, .jpg, .jpe, .jfif`
            );
            return;
        }

        let imageToSend: Uint8Array | null = null;
        const reader = new FileReader();
        reader.readAsArrayBuffer(selectedImage as File);
        reader.onload = async () => {
            const buffer = reader.result as ArrayBuffer;
            imageToSend = new Uint8Array(buffer);
            await httpRequests.addProfilePicture(loggedInUser.username, imageToSend);
            window.location.reload();
        };
    }

    useEffect(() => {
        setProfilePicture();
    }, []);

    return (
        <>
            <NavBar setLoggedInUser={setLoggedInUser} />
            <div id="profilePage">
                <div id="userInfo">
                    <img id="profilePicture" alt="Avatar" />
                    <h2>{loggedInUser.username}'s Profile</h2>
                    <p>Email: {loggedInUser.email}</p>
                    {!editProfilePictureOpen && (
                        <button type="button" onClick={openEditProfilePicture}>
                            Change profile picture
                        </button>
                    )}
                    {editProfilePictureOpen && (
                        <div id="editProfilePictureWrapper">
                            <label>Upload image</label>
                            <input
                                type="file"
                                id="profilePictureInput"
                                onChange={handleImageChange}
                                ref={inputRef}
                            ></input>{" "}
                            {selectedImage && (
                                <button
                                    type="button"
                                    className="profilePictureButton"
                                    onClick={submitImageProfilePictureChange}
                                >
                                    Upload
                                </button>
                            )}
                            <button
                                type="button"
                                className="profilePictureButton"
                                onClick={closeEditProfilePicture}
                            >
                                Cancel
                            </button>
                        </div>
                    )}
                </div>

                <div id="userItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"userItineraries"}
                        itinerariesBasedOn={"Your itineraries"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>

                <div id="likedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"likedItineraries"}
                        itinerariesBasedOn={"Liked itineraries"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>

                <div id="ratedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"ratedItineraries"}
                        itinerariesBasedOn={"Rated itineraries"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>
            </div>
        </>
    );
};

export default ProfilePage;
