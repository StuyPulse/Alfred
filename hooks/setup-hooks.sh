#!/bin/bash

cd "`git rev-parse --show-toplevel`"
#cp hooks/post-commit .git/hooks
#cp hooks/pre-commit .git/hooks
#cp hooks/commit-msg .git/hooks
ln -s hooks/post-commit .git/hooks/post-commit 
ln -s hooks/pre-commit .git/hooks/pre-commit 
ln -s hooks/commit-msg .git/hooks/commit-msg 
