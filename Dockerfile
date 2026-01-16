- name: Build and push
  uses: docker/build-push-action@v6
  with:
    context: .
    push: true
    tags: hsbapch/hsbapch-backend:sha-${{ github.sha }}