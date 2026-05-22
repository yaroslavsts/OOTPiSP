#!/usr/bin/env bash
set -euo pipefail

if [ ! -d build/classes ]; then
  ./build.sh
fi

lab="${1:-}"
shift || true

case "$lab" in
  lab1|Lab1) main_class="lab1.Main" ;;
  lab2|Lab2) main_class="lab2.Main" ;;
  lab3|Lab3) main_class="lab3.Main" ;;
  lab4|Lab4) main_class="lab4.Main" ;;
  lab5|Lab5) main_class="lab5.Main" ;;
  lab6|Lab6) main_class="lab6.Main" ;;
  *)
    echo "Usage: ./run.sh lab1|lab2|lab3|lab4|lab5|lab6 [arguments]"
    exit 1
    ;;
esac

if [ "$main_class" = "lab6.Main" ] && [ ! -f artifacts/plugins/lab6/triangle-storage-adapter.jar ]; then
  ./build.sh
fi

java -cp build/classes "$main_class" "$@"
