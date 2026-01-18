#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 게시글 생성 ==="
curl -X POST "$BASE_URL/api/posts" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "content": "첫 번째 게시글입니다!"
  }'
echo -e "\n"

echo "=== 전체 게시글 조회 ==="
curl -X GET "$BASE_URL/api/posts"
echo -e "\n"

echo "=== 게시글 상세 조회 (ID: 1) ==="
curl -X GET "$BASE_URL/api/posts/1"
echo -e "\n"

echo "=== 게시글 수정 (ID: 1) ==="
curl -X PUT "$BASE_URL/api/posts/1" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "content": "수정된 게시글 내용입니다."
  }'
echo -e "\n"

echo "=== 사용자별 게시글 조회 (userId: 1) ==="
curl -X GET "$BASE_URL/api/users/1/posts"
echo -e "\n"

echo "=== 게시글 삭제 (ID: 1) ==="
curl -X DELETE "$BASE_URL/api/posts/1" \
  -b cookies.txt
echo -e "\n"

echo "=== 페이징 조회 (page=0, size=5) ==="
curl -X GET "$BASE_URL/api/posts?page=0&size=5"
echo -e "\n"
