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
    name: string,
    cost: number,
    distance: number,
    duration: number,
    description: string;
}

export interface ItineraryDestination {
    itineraryID: number, // Foreign key
    destinationID: number, // Foreign key
    order: number,
}

export interface ItineraryDestinationJoined {
    itineraryID: number, // Foreign key
    destinationID: number, // Foreign key
    name: string,
    area: string,
    country: string,
    continent: string,
    order: number,
}

export interface Destination {
    id: number,
    name: string,
    area: string,
    country: string, // Foreign key
}

export interface Country {
    name: string,
    continent: string, // Foreign key
}

export interface Continent {
    name: string,
}