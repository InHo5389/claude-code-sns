#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 인용 작성 (postId: 1) ==="
curl -X POST "$BASE_URL/api/posts/1/quotes" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "content": "인용하면서 의견을 덧붙입니다."
  }'
echo -e "\n"

echo "=== 인용 목록 조회 (postId: 1) ==="
curl -X GET "$BASE_URL/api/posts/1/quotes"
echo -e "\n"

echo "=== 인용 삭제 (ID: 2) ==="
curl -X DELETE "$BASE_URL/api/quotes/2" \
  -b cookies.txt
echo -e "\n"
