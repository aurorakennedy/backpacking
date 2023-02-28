export interface LoggedInUser {
    username: string;
    email: string;
}

export interface User {
    username: string;
    email: string;
    password: string;
}

export interface Itinerary {
    id: number,
    userEmail: string, // Foreign key
    lastChanged: Date,
    title: string,
    cost: number,
    distance: number,
    duration: number,
    description: string;
}

export interface ItineraryDestinationJoined {
    itineraryID: number, // Foreign key
    destinationName: string, // Foreign key
    country: string,
    order: number, 
    description: string,
}

// Maybe unused

export interface Destination {
    name: string,
    country: string, // Foreign key
    description: string,
}

export interface Country {
    name: string,
    continent: string, // Foreign key
}

export interface Continent {
    name: string,
}