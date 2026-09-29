#!/usr/bin/env bash
set -euo pipefail

repo_root="$(git rev-parse --show-toplevel)"
cd "$repo_root"

branch="$(git branch --show-current)"
if [[ -z "$branch" ]]; then
  echo "Cannot push from a detached HEAD." >&2
  exit 1
fi

echo "Pushing $branch to Gitee..."
if git push gitee "$branch"; then
  gitee_ok=true
else
  gitee_ok=false
fi

echo "Pushing $branch to GitHub..."
if git push github "$branch"; then
  github_ok=true
else
  github_ok=false
fi

if [[ "$gitee_ok" == true && "$github_ok" == true ]]; then
  echo "Pushed $branch to both remotes."
else
  echo "Push results: Gitee=$gitee_ok, GitHub=$github_ok" >&2
  exit 1
fi
