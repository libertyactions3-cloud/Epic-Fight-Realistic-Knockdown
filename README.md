# Epic Fight: Realistic Knockdown

A NeoForge addon for Epic Fight 21.17.3.1 on Minecraft 1.21.1. It allows entities to receive follow-up damage during hit-stun and knockdown, and replaces the ongoing stun reaction according to the kind of damage received.

## Supported versions

- Minecraft: 1.21.1
- NeoForge: 21.1.219
- Epic Fight: 21.17.3.1-mc1.21.1-neoforge
- Java: 21

Epic Fight must be installed. The mod declares it as a required dependency.

## Behavior

- Follow-up damage is accepted while an Epic Fight entity is in a hit reaction or knockdown. The check is not restricted to attacks made with an Epic Fight-capable held item.
- If the follow-up damage is melee, Epic Fight's normal entity-specific hit reaction is used. If Epic Fight's stun calculation would otherwise resolve to `NONE` during an existing stun, the addon uses a short hit reaction instead.
- If the follow-up damage is not melee, the reaction is replaced with Epic Fight's `NEUTRALIZE` reaction. On humanoid/player patches this is Epic Fight's built-in `BIPED_COMMON_NEUTRALIZED` animation (`biped/skill/guard_break1`), which is the crouched/kneeling guard-break pose.
- The same reaction selection is applied to non-Epic-Fight damage sources and Epic Fight sources marked `NO_STUN`, provided the target was already stunned when the damage event began.
- Epic Fight's own `IS_MELEE` damage-type tag is used to classify melee attacks, rather than checking weapon names or item capabilities.

The visual animation is limited by the target's Epic Fight entity patch. Patches that provide a `NEUTRALIZE` hit animation use their own compatible animation. Epic Fight's generic `GlobalMobPatch` has no animator or entity model, so it can have its stun timer refreshed but cannot display the crouching/kneeling pose. Entities without an Epic Fight hit-animation patch likewise cannot display a new Epic Fight animation until a compatible patch/animation is provided.

## Build

Use a Java 21 JDK.

Windows (PowerShell):

```powershell
.\gradlew.bat clean build
```

Linux/macOS:

```bash
./gradlew clean build
```

The built mod JAR is written to `build/libs/`.

## Porting notes and attribution

The original knockdown behavior was ported from the knockdown-related `EntityStateMixin` in [xssfww/EpicFight_Improve](https://github.com/xssfww/EpicFight_Improve), originally authored by XcS and distributed under the MIT License.

This project targets Epic Fight's 1.21.1 API, including `EpicFightDamageSource.getUsedItem()`, `EpicFightDamageTypeTags.IS_MELEE`, and `StunType.NEUTRALIZE`.

Target Epic Fight version: [21.17.3.1-mc1.21.1-neoforge](https://modrinth.com/mod/epic-fight/version/8HHhJt6i).
