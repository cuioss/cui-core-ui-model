# Log Messages for UI Model

All messages follow the format: UI_MODEL-[identifier]: [message]

## INFO Level (001-099)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-001 | ResultObject | Result object created with state '%s' | Logged when a new result object is created |
| UI_MODEL-002 | ResultObject | Result mapped from '%s' to '%s' | Logged when a result is mapped from one type to another |
| UI_MODEL-003 | Service | Service state changed to '%s' | Logged when a service changes its state |

## WARN Level (100-199)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-100 | ResultObject | Invalid attempt to access result in state '%s' | Logged when trying to access a result in an invalid state |
| UI_MODEL-101 | DisplayMessageFormat | No message format arguments provided. Consider using LabeledKey instead. | Logged when a message format is built without arguments |
| UI_MODEL-102 | ResultObject | Result detail is missing for non-valid state '%s' | Logged when result detail is missing for a non-valid state |
| UI_MODEL-103 | Service | Service not ready: %s | Logged when attempting to use a service that is not ready |
| UI_MODEL-104 | ResultObject | Failed to create result object: %s | Logged when result object creation fails |

## ERROR Level (200-299)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-200 | Service | Service initialization failed: %s | Logged when service initialization fails |
| UI_MODEL-201 | MessageProvider | MessageProvider threw error: %s | Logged when message provider encounters an error |

## DEBUG Level (500-599)

| ID | Component | Message | Description |
|----|-----------|---------|-------------|
| UI_MODEL-500 | ResultObject | Result detail added: %s | Debug information when result detail is added |
| UI_MODEL-501 | Service | Service state: %s | Debug information about service state |
