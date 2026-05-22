# Lab6

This is the patterns assignment implementation based on Lab5.

The friend's plugin is `triangleplugin`: it defines a `Triangle` shape and a `TriangleDescriptor` from another graphic-editor API. It cannot be loaded by this project directly because it does not implement `shared.StoragePlugin`.

The adapter plugin `TriangleStorageAdapter` wraps that triangle functionality and exposes it as a storage plugin named `triangle-shape-adapter`. In the application it appears near `xor-encryption` and `shift-encryption` as another selectable processing strategy.

Patterns used:

- Adapter: `TriangleStorageAdapter` converts `ShapeDescriptor` / `Shape` into `StoragePlugin`.
- Strategy: all storage plugins implement `StoragePlugin` and are selected at runtime.
- Factory Method: `TypeDef.create(...)` creates concrete athlete objects through stored factory functions.
