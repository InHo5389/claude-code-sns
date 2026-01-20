#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== 미디어 생성 (IMAGE) ==="
curl -X POST "$BASE_URL/api/media" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "mediaType": "IMAGE"
  }'
echo -e "\n"

echo "=== 미디어 생성 (VIDEO) ==="
curl -X POST "$BASE_URL/api/media" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "mediaType": "VIDEO"
  }'
echo -e "\n"

echo "=== 미디어 상세 조회 (ID: 1) ==="
curl -X GET "$BASE_URL/api/media/1"
echo -e "\n"

echo "=== 내 미디어 목록 조회 ==="
curl -X GET "$BASE_URL/api/media" \
  -b cookies.txt
echo -e "\n"

echo "=== 사용자별 미디어 조회 (userId: 1) ==="
curl -X GET "$BASE_URL/api/users/1/media"
echo -e "\n"

echo "=== 상태별 미디어 조회 (INIT) ==="
curl -X GET "$BASE_URL/api/media/status/INIT" \
  -b cookies.txt
echo -e "\n"

echo "=== 업로드 초기화 (ID: 1) ==="
curl -X PUT "$BASE_URL/api/media/1/init-upload" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "uploadId": "upload-12345",
    "path": "/media/images/2024/01/image-001.jpg"
  }'
echo -e "\n"

echo "=== 업로드 완료 표시 (ID: 1) ==="
curl -X PUT "$BASE_URL/api/media/1/uploaded" \
  -b cookies.txt
echo -e "\n"

echo "=== 처리 완료 표시 (ID: 1) ==="
curl -X PUT "$BASE_URL/api/media/1/completed" \
  -b cookies.txt
echo -e "\n"

echo "=== 처리 실패 표시 (ID: 2) ==="
curl -X PUT "$BASE_URL/api/media/2/failed" \
  -b cookies.txt
echo -e "\n"

echo "=== 메타데이터 업데이트 (ID: 1) ==="
curl -X PUT "$BASE_URL/api/media/1/attributes" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "attributes": {
      "width": 1920,
      "height": 1080,
      "format": "jpeg",
      "size": 1048576
    }
  }'
echo -e "\n"

echo "=== 미디어 삭제 (ID: 1) ==="
curl -X DELETE "$BASE_URL/api/media/1" \
  -b cookies.txt
echo -e "\n"

echo "=== 페이징 조회 (page=0, size=10) ==="
curl -X GET "$BASE_URL/api/media?page=0&size=10" \
  -b cookies.txt
echo -e "\n"
