# quasar-fire
This is an API to locate the position and the message from an emitter received through three receptors
### Execution

-  Clone the project to a local repository.
-  Run with you preferred IDE \src\main\java\com\challenge\quasarfire\communications\CommunicationsApplication.java;
-  The application will run in the port 8080 by default. Make HTTP request using a tool like Postman with headers Content-Type = application/json, Accept = application/json
-   the services available are:
-  POST -> /quasarfire/topsecret
-  sample body: {
    "satellites": [
        { 
            "name": "kenobi", 
            "distance": 500.0, 
            "message": [
                "este",
                "",
                "",
                "mensaje",
                ""
            ]
        },
        { 
            "name": "skywalker", 
            "distance": 570.0, 
            "message": [
                "",
                "es",
                "",
                "",
                "secreto"
            ]
        },
        { 
            "name": "sato", 
            "distance": 850.0 ,
            "message": [
                "este",
                "",
                "un",
                "",
                ""
            ]
        }
    ]
}
-  GET ->  /topsecret_split/{satellite_name}
-  sample body:  { 
            "distance": 500.0, 
            "message": [
                "este",
                "",
                "",
                "mensaje",
                ""
            ]
}
