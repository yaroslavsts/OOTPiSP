# OOTPiSP Labs

Java laboratory works for object-oriented programming, serialization, plugin architecture, and design patterns.

The project is organized as a sequence of labs. Earlier labs introduce basic OOP concepts, while later labs reuse the common model and extend it with serialization, plugins, encryption, and pattern-based adaptation.

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
