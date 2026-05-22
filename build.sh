#!/usr/bin/env bash
set -euo pipefail

rm -rf build artifacts
mkdir -p build/classes artifacts/plugins/lab4 artifacts/plugins/lab5 artifacts/plugins/lab6

find Shared/src Lab1/src Lab2/src Lab3/src Lab4/src Lab5/src Lab6/src -name "*.java" | sort > build/sources.txt
javac -d build/classes @build/sources.txt

build_plugin() {
  local name="$1"
  local source_dir="$2"
  local jar_file="$3"
  local class_path="$4"
  local output_dir="build/plugin-${name}"

  mkdir -p "$output_dir/classes"
  find "$source_dir" -name "*.java" | sort > "$output_dir/sources.txt"
  javac -cp "$class_path" -d "$output_dir/classes" @"$output_dir/sources.txt"

  if [ -d "$source_dir/META-INF" ]; then
    cp -R "$source_dir/META-INF" "$output_dir/classes/"
  fi

  jar cf "$jar_file" -C "$output_dir/classes" .
}

build_plugin "cyclist" "Lab4/Plugins/Entity.Cyclist/src" "artifacts/plugins/lab4/entity-cyclist.jar" "build/classes"
build_plugin "xor" "Lab5/Plugins/Storage.XorEncryption/src" "artifacts/plugins/lab5/xor-encryption.jar" "build/classes"
build_plugin "shift" "Lab5/Plugins/Storage.ShiftEncryption/src" "artifacts/plugins/lab5/shift-encryption.jar" "build/classes"
build_plugin "friend" "Lab6/Plugins/Friend.LegacyBase64/src" "artifacts/plugins/lab6/friend-legacy.jar" "build/classes"
build_plugin "adapter" "Lab6/Plugins/Adapter.LegacyBase64/src" "artifacts/plugins/lab6/friend-adapter.jar" "build/classes:artifacts/plugins/lab6/friend-legacy.jar"

echo "Build completed."
