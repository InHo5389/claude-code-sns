#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 회원가입 ==="
curl -X POST "$BASE_URL/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "nickname": "testuser"
  }'
echo -e "\n"

echo "=== 사용자 조회 (ID: 1) ==="
curl -X GET "$BASE_URL/api/users/1"
echo -e "\n"

echo "=== 전체 사용자 조회 ==="
curl -X GET "$BASE_URL/api/users"
echo -e "\n"

echo "=== 사용자 정보 수정 (ID: 1) ==="
curl -X PUT "$BASE_URL/api/users/1" \
  -H "Content-Type: application/json" \
  -d '{
    "password": "newpassword123",
    "nickname": "updateduser"
  }'
echo -e "\n"

echo "=== 사용자 삭제 (ID: 1) ==="
curl -X DELETE "$BASE_URL/api/users/1"
echo -e "\n"

echo "=== Validation 테스트 (잘못된 이메일) ==="
curl -X POST "$BASE_URL/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "invalid-email",
    "password": "short",
    "nickname": "a"
  }'
echo -e "\n"
