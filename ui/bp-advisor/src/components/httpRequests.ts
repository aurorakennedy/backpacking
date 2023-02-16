import React from "react";

interface User {
    username: string;
    email: string;
    password: string;
}

async function getUser(userId: number): Promise<User> {
    const response: Response = await fetch(`http://localhost:8080/users/${userId}`);
    if (!response.ok) {
        throw new Error('Failed to fetch user');
    }
    const user: User = await response.json();
    return user;
}

async function register(user: User): Promise<void> {
    const response: Response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error('Failed to register user');
    }
}

async function login(request: string[]): Promise<boolean> {
    const response: Response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(request),
    });
    if (!response.ok) {
        throw new Error('Failed to log in');
    }
    const loggedIn: boolean = await response.json();
    return loggedIn;
}

async function updateUser(userId: number, user: User): Promise<void> {
    const response: Response = await fetch(`http://localhost:8080/users/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error('Failed to update user');
    }
}

async function deleteUser(userId: number): Promise<void> {
    const response: Response = await fetch(`http://localhost:8080/users/${userId}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete user');
    }

}

const httpRequests = {
    getUser,
    register,
    login,
    updateUser,
    deleteUser
}

export default httpRequests;
