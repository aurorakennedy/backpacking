import React, { useState, useEffect } from "react";

interface LikeButtonProps {
    initialLiked: boolean;
    id: string;
}

const LikeButton: React.FC<LikeButtonProps> = ({ initialLiked, id }) => {
    const [liked, setLiked] = useState(initialLiked);
    const [bgColor, setBgColor] = useState("#d4eac9cc");
    const [hovered, setHovered] = useState(false);

    useEffect(() => {
        setLiked(initialLiked);
    }, [initialLiked]);

    const handleClick = () => {
        const newLiked = !liked;
        setLiked(newLiked);
        setBgColor(liked ? "lightgray" : "pink");

        setTimeout(() => {
            setBgColor("#d4eac9cc");
        }, 500);
    };

    const buttonStyle = {
        backgroundColor: hovered ? bgColor : "#ececec",
        border: hovered ? "2px solid d4eac9cc" : "1px solid #ececec",
        borderRadius: "5px",
        padding: "5px 10px",
        fontSize: "16px",
        cursor: "pointer",
        outline: "none",
        boxShadow: "none",
        transition: "all 0.3s ease-in-out",
    };

    return (
        <button
            id={id}
            onClick={handleClick}
            style={buttonStyle}
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
        >
            {liked ? "❤️Liked" : "🤍Like"}
        </button>
    );
};

export default LikeButton;
