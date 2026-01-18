#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 답글 작성 (postId: 1) ==="
curl -X POST "$BASE_URL/api/posts/1/replies" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "content": "첫 번째 답글입니다!"
  }'
echo -e "\n"

echo "=== 답글 목록 조회 (postId: 1) ==="
curl -X GET "$BASE_URL/api/posts/1/replies"
echo -e "\n"

echo "=== 답글 삭제 (ID: 2) ==="
curl -X DELETE "$BASE_URL/api/replies/2" \
  -b cookies.txt
echo -e "\n"
