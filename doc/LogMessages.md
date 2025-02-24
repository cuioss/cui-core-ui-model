# Log Messages for cui-core-ui-model

All messages follow the format: UI_MODEL-[identifier]: [message]

## INFO Level (001-099)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-001 | Result | Created result object with state '%s' | Logged when a new result object is created |
| UI_MODEL-002 | Result | Result mapped from '%s' to '%s' | Logged when a result is mapped from one type to another |

## WARN Level (100-199)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-101 | Result | Attempted to access result when state is '%s' | Logged when attempting to access a result in an invalid state |
| UI_MODEL-102 | Result | Result detail is missing for non-valid state '%s' | Logged when a result detail is required but missing |
| UI_MODEL-104 | Result | Failed to create result object: %s | Logged when result object creation fails |
