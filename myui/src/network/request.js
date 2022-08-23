import axios from "axios";

export function request(params) {
    const instance = axios.create({
        baseURL: 'http://localhost:8081',
        timeout: 5000,
        headers: {
            "Content-Type": 'application/json;charset=utf-8'
        }
    });

    return instance(params);
}