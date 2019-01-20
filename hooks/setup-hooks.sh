#!/bin/bash

cd "`git rev-parse --show-toplevel`"
cp hooks/post-commit .git/hooks
