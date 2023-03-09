import {
    Itinerary,
    ItineraryAndDestinations,
    ItineraryDestination,
    User,
} from "./types";

async function getUser(userId: number): Promise<User> {
    const response: Response = await fetch(
        `http://localhost:8080/users/${userId}`
    );
    if (!response.ok) {
        throw new Error("Failed to fetch user");
    }
    const user: User = await response.json();
    return user;
}

async function register(user: User): Promise<User> {
    const response: Response = await fetch("http://localhost:8080/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error("Failed to register user");
    }
    try {
        const loggedInUser: User = await response.json();
        return loggedInUser;
    } catch (error) {
        //THIS SHOULD BE FIXED: UGLY!
        return {
            username: "failed",
            email: "failed",
            password: "failed",
        };
    }
}

async function login(user: User): Promise<User> {
    const response: Response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
    });
    console.log("Response: " + response.status);
    if (!response.ok) {
        throw new Error("Failed to log in");
    }
    try {
        const loggedInUser: User = await response.json();
        return loggedInUser;
    } catch (error) {
        //THIS SHOULD BE FIXED: UGLY!
        return {
            username: "failed",
            email: "failed",
            password: "failed",
        };
    }
}

async function updateUser(userId: number, user: User): Promise<void> {
    const response: Response = await fetch(
        `http://localhost:8080/users/${userId}`,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        }
    );
    if (!response.ok) {
        throw new Error("Failed to update user");
    }
}

async function deleteUser(userId: number): Promise<void> {
    const response: Response = await fetch(
        `http://localhost:8080/users/${userId}`,
        {
            method: "DELETE",
        }
    );
    if (!response.ok) {
        throw new Error("Failed to delete user");
    }
}

async function getItineraryAndDestinationsById(
    itineraryId: number
): Promise<ItineraryAndDestinations> {
    const response: Response = await fetch(
        `http://localhost:8080/itineraryanddestinations/${itineraryId}`
    );
    if (!response.ok) {
        throw new Error("Failed to fetch itinerary");
    }
    const itineraryAndDestinations: ItineraryAndDestinations =
        await response.json();
    return itineraryAndDestinations;
}

async function getItinerariesByUserEmail(
    userEmail: string
): Promise<Itinerary[]> {
    const response: Response = await fetch(
        `http://localhost:8080/getitineraries/${userEmail}`
    );
    if (!response.ok) {
        throw new Error("Failed to fetch itineraries");
    }
    const itineraries: Itinerary[] = await response.json();
    return itineraries;
}

async function addItinerary(itinerary: Itinerary): Promise<void> {
  const response: Response = await fetch("http://localhost:8080/itinerary", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(itinerary),
  });
  if (!response.ok) {
    throw new Error("Failed to add itinerary");
  }
}

// AURORA: 

async function deletItinerary(email: string, title : string): Promise<void> {
    const response: Response = await fetch(`http://localhost:8080/deleteitinerary/${title}`, {
      
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(email), 
    
      
    });
    if (!response.ok) {
      throw new Error("Failed to delete itinerary");
    }
  }
  
  
  // 

async function getItineraryDestinations(
    itineraryID: number
): Promise<ItineraryDestination[]> {
    const response: Response = await fetch(
        `http://localhost:8080/itinerarydestinations/${itineraryID}`
    );
    if (!response.ok) {
        throw new Error("Failed to fetch itinerary destinations");
    }
    const itineraryDestinations: ItineraryDestination[] = await response.json();
    return itineraryDestinations;
}

async function addItineraryAndDestinations(
    itineraryAndDestinations: ItineraryAndDestinations
): Promise<void> {
    const response: Response = await fetch(
        "http://localhost:8080/additineraryanddestinations",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(itineraryAndDestinations),
        }
    );
    if (!response.ok) {
        throw new Error("Failed to add itinerary and destinations");
    }
}

async function searchByKeyword(keyword: string): Promise<Itinerary[]> {
    const response: Response = await fetch(
        `http://localhost:8080/itineraries/`
    );
    if (!response.ok) {
        throw new Error("Failed to fetch itineraries by keyword");
    }
    const itineraries: Itinerary[] = await response.json();
    return itineraries;
}

async function getRecommendedItineraries(
    userEmail: string
): Promise<Itinerary[]> {
    const response: Response = await fetch(
        `http://localhost:8080/getrecommendeditineraries/${userEmail}`
    );
    if (!response.ok) {
        throw new Error("Failed to fetch itineraries by keyword");
    }
    const recommendedItineraries: Itinerary[] = await response.json();
    return recommendedItineraries;
}

async function updateLikeOnItinerary(
    email: String,
    itineraryId: number
): Promise<void> {
    const response: Response = await fetch(
        `http://localhost:8080/likes/${email}/${itineraryId}`,
        {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
        }
    );
    if (!response.ok) {
        throw new Error("Failed to update like");
    }
}

async function itineraryIsLiked(
    email: String,
    itineraryId: number
): Promise<boolean> {
    const response: Response = await fetch(
        `http://localhost:8080/likes/${email}/${itineraryId}`
    );
    if (!response.ok) {
        throw new Error("Failed to check if itinerary is liked");
    }
    const itineraryIsLiked: boolean = await response.json();
    return itineraryIsLiked;
}

const httpRequests = {
    getUser,
    register,
    login,
    updateUser,
    deleteUser,
    addItinerary,
    deletItinerary,
    getItineraryAndDestinationsById,
    getItinerariesByUserEmail,
    getItineraryDestinations,
    searchByKeyword,
    addItineraryAndDestinations,
    getRecommendedItineraries,
    updateLikeOnItinerary,
    itineraryIsLiked,
};

export default httpRequests;
