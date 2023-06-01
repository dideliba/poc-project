import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { target: 100, duration: '10s' },
    { target: 0, duration: '30s' },
  ],
};

export default function () {
  const url = 'http://localhost/api/user-service/v1/users?id=647642c00b40224aba2cc5fc';
  const payload = {"username":"fkkf", "email":"newTest@test.com","firstname":null,"lastname":null};

  const headers = {
    'Content-Type': 'application/json',
  };

  const result = http.get(url, );

  console.log('Response:', result.body);	
  check(result, {
    'http response status code is 200': result.status === 200,
  });
}

