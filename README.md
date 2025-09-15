# Android Automated MVI Template Generator

This project is a **sample project** demonstrating automation for generating MVI (Model-View-Intent) based components in Android applications. It provides a Gradle plugin that automatically creates
complete MVI architecture components with proper structure and boilerplate code.

### Parameters

- **`featurePath`** - The feature name (e.g., `test`, `profile`, `settings`)
- **`basePackage`** - The base package for your feature modules (e.g., `com.yunho.feature`)

### Example

To create a "Profile" feature:

```bash
./gradlew -PfeaturePath=profile -PbasePackage=com.yunho.feature generateMviTemplate
```

This will generate:

- `ProfileState.kt`
- `ProfileEvent.kt`
- `ProfileSideEffect.kt`
- `Profile.kt` (UI)
- `ProfileViewModel.kt`
- `ProfileDialog.kt`
- `ProfileDialogState.kt`

## Generation Result

```
feature/src/main/kotlin/
└── {basePackage}/
    └── {featurePath}/
        ├── intent/
        │   ├── {FeatureName}State.kt
        │   ├── {FeatureName}Event.kt
        │   └── {FeatureName}SideEffect.kt
        └── view/
            ├── {FeatureName}.kt
            ├── model/
            │   └── {FeatureName}ViewModel.kt
            └── component/
                ├── {FeatureName}Dialog.kt
                └── {FeatureName}DialogState.kt
```
