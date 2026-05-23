# OOTPiSP Labs

Java Swing laboratory works for object-oriented programming, serialization, plugins, and design patterns.

## Structure

```text
Lab1   Inheritance, abstraction, and polymorphism with drawable figures.
Lab2   Interfaces, creators, drawers, and a simple figure registry.
Lab3   Text serialization and deserialization of athlete objects.
Lab4   Entity plugins loaded from external JAR files.
Lab5   Storage plugins for selectable encryption strategies.
Lab6   Design patterns: Adapter, Strategy, and Factory Method.
Shared Common model, UI, registry, storage, plugin loader, and pipeline code.
```

## Build

Run from the repository root:

```bash
./build.sh
```

The build script compiles all labs and creates plugin JAR files under `artifacts/plugins`.

## Run

```bash
./run.sh lab1
./run.sh lab2
./run.sh lab3
./run.sh lab4
./run.sh lab5
./run.sh lab6
```

## Lab 6

Lab 6 is based on Lab 5 and adapts a friend's triangle plugin to this project's storage plugin system.

The friend's plugin provides:

- `Triangle`
- `TriangleDescriptor`
- `Shape`
- `ShapeDescriptor`

The project expects storage plugins that implement `shared.StoragePlugin`, so the friend's shape plugin cannot be used directly. `TriangleStorageAdapter` adapts it to the expected interface and exposes it as:

```text
triangle-shape-adapter
```

The adapter uses the triangle shape to choose byte masks during file processing. The plugin appears in the application together with the Lab 5 storage strategies.

Implemented patterns:

- Adapter: `TriangleStorageAdapter` adapts the friend's triangle API to `StoragePlugin`.
- Strategy: storage plugins are selectable processing algorithms.
- Factory Method: `TypeDef.create(...)` creates concrete athlete objects through stored factory functions.
