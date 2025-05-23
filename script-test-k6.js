import http from 'k6/http';
import { check } from 'k6';

export let options = {
    scenarios: {
        get_product: {
            executor: 'constant-vus',
            vus: 1000, // 5 CCU cho API GET /users
            duration: '1s',
            exec: 'getProduct',
        },
        post_login: {
            executor: 'constant-vus',
            vus: 100, // 2 CCU cho API POST /login
            duration: '1s',
            exec: 'postLogin',
        },
        put_profile: {
            executor: 'constant-vus',
            vus: 3, // 3 CCU cho API PUT /profile
            duration: '15s',
            exec: 'putProfile',
        },
        delete_post: {
            executor: 'constant-vus',
            vus: 1, // 1 CCU cho API DELETE /posts
            duration: '5s',
            exec: 'deletePost',
        },
    },
};

// 1. GET /users
export function getProduct() {
    const res = http.get('https://api.example.com/users');
    check(res, {
        'GET /users - status 200': (r) => r.status === 200,
    });
}

// 2. POST /login
export function postLogin() {
    const payload = JSON.stringify({
        username: 'testuser',
        password: 'password123',
    });
    const headers = { 'Content-Type': 'application/json' };
    const res = http.post('https://localhost:9002/api/v1/user/login', payload, { headers });
    check(res, {
        'POST /login - status 200': (r) => r.status === 200,
    });
}

// 3. PUT /profile
export function putProfile() {
    const payload = JSON.stringify({
        name: 'Updated Name',
    });
    const headers = { 'Content-Type': 'application/json' };
    const res = http.put('https://api.example.com/profile/1', payload, { headers });
    check(res, {
        'PUT /profile - status 200': (r) => r.status === 200,
    });
}

// 4. DELETE /posts
export function deletePost() {
    const res = http.del('https://api.example.com/posts/1');
    check(res, {
        'DELETE /posts - status 200': (r) => r.status === 200,
    });
}
