import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { target: 100, duration: '10s' },
    { target: 0, duration: '30s' },
  ],
};

export default function () {
  const url = 'http://localhost/api/user-service/v1/users';
  const payload = {"username":"fkkf", "email":"newTest@test.com","firstname":null,"lastname":null};

  const headers = {
    'Content-Type': 'application/json',
  };

  const result = http.post(url, JSON.stringify(payload), { headers: headers });

  console.log('Response:', result.body);	
  check(result, {
    'http response status code is 201': result.status === 201,
  });
}

