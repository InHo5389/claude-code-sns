#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 리포스트 (postId: 1) ==="
curl -X POST "$BASE_URL/api/posts/1/reposts" \
  -b cookies.txt
echo -e "\n"

echo "=== 리포스트 목록 조회 (postId: 1) ==="
curl -X GET "$BASE_URL/api/posts/1/reposts"
echo -e "\n"

echo "=== 리포스트 삭제 (ID: 2) ==="
curl -X DELETE "$BASE_URL/api/reposts/2" \
  -b cookies.txt
echo -e "\n"
