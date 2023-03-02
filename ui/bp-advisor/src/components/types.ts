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
  writtenDate: Date;
  title: string;
  cost: number;
  estimatedTime: number;
  description: string;
  image: string;
}

export interface ItineraryDestination {
  itineraryID: number; // Foreign key
  order: number;
  destinationName: string; // Foreign key
  country: string;
}

//Possibly used for top lists
export interface Destination {
  name: string;
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
