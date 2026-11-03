# Epic Fight: Realistic Knockdown

A small NeoForge addon that lets Epic Fight weapon attacks damage living entities during the **knockdown** state.

## Supported versions

- Minecraft: 1.21.1
- NeoForge: 21.1.219
- Epic Fight: 21.17.3.1-mc1.21.1-neoforge
- Java: 21

Epic Fight must be installed. The mod declares it as a required dependency.

## Behavior

When Epic Fight evaluates `EntityState.attackResult(DamageSource)`, this addon returns `SUCCESS` if all of the following are true:

1. The target is currently in Epic Fight's knockdown state.
2. The incoming source is an `EpicFightDamageSource`.
3. The source's used item has an Epic Fight item capability.

This removes the knockdown-specific hit rejection for Epic Fight weapon attacks. It does not disable every form of stun protection or change Epic Fight's other hit states.

## Build

Use a Java 21 JDK.

Windows:

```powershell
.`gradlew.bat clean build
```

Linux/macOS:

```bash
./gradlew clean build
```

The built mod JAR is written to `build/libs/`.

## Porting notes and attribution

This port is based on the knockdown-related `EntityStateMixin` from [xssfww/EpicFight_Improve](https://github.com/xssfww/EpicFight_Improve), originally authored by XcS and distributed under the MIT License.

Epic Fight's 1.21.1 API uses `EpicFightDamageSource.getUsedItem()` and `EpicFightCapabilities.getItemCapability(...)`; this port uses those APIs instead of the older `getHurtItem()` / lazy-capability access used by the original source.

Target Epic Fight version: [21.17.3.1-mc1.21.1-neoforge](https://modrinth.com/mod/epic-fight/version/8HHhJt6i).
