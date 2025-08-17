# AosAgentTemplate

AosAgentTemplate is an Android system service and library designed to provide privileged OS extension features for AOSP-based devices. It enables client applications to perform system-level operations that are not available through the standard Android SDK, using a secure, AIDL-based IPC mechanism.

## Features

- **Foreground Android Service**: Runs as a persistent foreground service to ensure reliability and system-level access.
- **AIDL Interfaces**: Exposes system management APIs via AIDL for secure inter-process communication.
- **System Operations**: Supports operations such as retrieving device serial number and other privileged actions.
- **Modular Design**: Separates service implementation, client library, and AIDL interfaces for maintainability.

## Project Structure

```
AosAgentTemplate/
├── service/         # Android service implementation
│   └── src/main/java/com/ttx/osagent/
│       └── AosAgentService.java
├── lib/             # Client library for accessing the service
│   └── java/com/ttx/osextension/
│       └── impl/v1_0/system/SystemManager.java
├── aidl/            # AIDL interface definitions
│   └── com/ttx/osagent/
│       ├── IAosAgentService.aidl
│       └── ISystemManager.aidl
├── example/         # Example application implementation
└── README.md
```

## How It Works

- **AosAgentLibExample**: An Android UI application that binds and uses the API that the `Service` provides.
- **AosAgentService**: An Android `Service` running in the foreground, exposing privileged operations via AIDL.
- **SystemManager**: Client-side implementation that communicates with the service using the defined AIDL interfaces.
- **AIDL Interfaces**: Define the contract between the service and clients, ensuring type-safe and secure IPC.

## Usage

1. **Build and install the service APK** on your AOSP device.
2. **Bind to the service** from your client app using the provided library.
3. **Call system operations** (e.g., get device serial number) through the `SystemManager` interface.

## Example

```java
// Bind to AosAgentService and obtain SystemManager
SystemManager systemManager = aosAgentManager.getSystemManager();
String serial = systemManager.getSerialNumber();
```

## Requirements

- Android 8.0 (Oreo) or higher
- System-level privileges for the service APK

## Security

- Only authorized clients should be allowed to bind to the service.
- All IPC is performed using AIDL with explicit interface checks.

## License

Proprietary. All rights reserved.

---

*This project is intended for use on Custom AOSP-based devices