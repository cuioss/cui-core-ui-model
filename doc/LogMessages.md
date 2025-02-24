# Log Messages for cui-core-ui-model

All messages follow the format: UI_MODEL-[identifier]: [message]

## INFO Level (001-099)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-001 | Result | Created result object with state '%s' | Logged when a new result object is created |
| UI_MODEL-002 | Result | Mapped result from type '%s' to type '%s' | Logged when a result is mapped from one type to another |
| UI_MODEL-003 | Service | Service '%s' changed state to '%s' | Logged when a service changes its state |

## WARN Level (100-199)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-100 | Result | Attempted to access result when state is '%s' | Logged when attempting to access a result in an invalid state |
| UI_MODEL-101 | Result | Result detail is missing for non-valid state '%s' | Logged when a result detail is required but missing |
| UI_MODEL-102 | Service | Service '%s' accessed before initialization | Logged when attempting to use a service that isn't ready |

## ERROR Level (200-299)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-200 | Result | Failed to create result object: %s | Logged when result object creation fails |
| UI_MODEL-201 | Service | Failed to initialize service '%s': %s | Logged when service initialization fails |
| UI_MODEL-202 | Message | Error in message provider: %s | Logged when message provider encounters an error |

## DEBUG Level (500-599)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-500 | Result | Added result detail: %s | Debug information about result detail addition |
| UI_MODEL-501 | Service | Service '%s' debug info: %s | Debug information about service state |
