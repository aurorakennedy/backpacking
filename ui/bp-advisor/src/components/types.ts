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
    id: number;
    writerEmail: string; // Foreign key
    writtenDate: string;
    title: string;
    cost: number;
    estimatedTime: number;
    description: string;
    image: string;
}

export interface ItineraryDestination {
    itineraryId: number; // Foreign key
    order: number;
    destinationName: string; // Foreign key
    country: string;
}

//Possibly used for top lists
export interface Destination {
    destinationName: string;
    country: string; // Foreign key
    description: string;
}

export interface Country {
    name: string;
    continent: string; // Foreign key
}

export interface Continent {
    name: string;
}

export interface ItineraryAndDestinations {
    itinerary: Itinerary;
    destinations: Destination[];
}

export interface ItineraryAndDestinationsWithImage {
    itinerary: Itinerary;
    destinations: Destination[];
    imageByteArray: number[] | null;
}

export interface ItineraryWithImage {
    itinerary: Itinerary;
    imageByteArray: number[] | null;
}
