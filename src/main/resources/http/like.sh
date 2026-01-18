#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 좋아요 (postId: 1) ==="
curl -X POST "$BASE_URL/api/posts/1/likes" \
  -b cookies.txt
echo -e "\n"

echo "=== 좋아요 여부 확인 (postId: 1) ==="
curl -X GET "$BASE_URL/api/posts/1/likes/me" \
  -b cookies.txt
echo -e "\n"

echo "=== 좋아요 취소 (postId: 1) ==="
curl -X DELETE "$BASE_URL/api/posts/1/likes" \
  -b cookies.txt
echo -e "\n"
