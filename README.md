### End Points

    1. admin signup -    http://[hostname:port]/inventory/v1/admin         (Method - POST)
            
        {
            "adminName":"admin1",
            "adminEmail":"admin1@gmail.com",
            "adminPassword":"123456",
            "dateOfBirth":"2001-05-23"
        }

    2. admin login  -    http://[hostname:port]/inventory/v1/admin/login         (Method - POST)
               
        { 
            "adminEmail": "admin1@gmail.com",
            "adminPassword":"123456"
        }

    3. saving Item  -    http://[hostname:port]/inventory/v1/stationary_item/add_item    (Method - POST)

        {

            "itemName": "eraser",
            "quantity": 100,
            "returnable": true,
            "maxDays": 5
        }

    4. fetch Item  -     http://[hostname:port]/inventory/v1/stationary_item/[id]    (Method - GET)
    5. fetch all Items - http://[hostname:port]/inventory/v1/stationary_item         (Method - GET)
    6. update Item -     http://[hostname:port]/inventory/v1/stationary_item/[id]    (Method - PATCH)

        {
            "quantity": 37, //(optional)
            "returnable": true, //(optional)
            "maxDays":5 //(optional)
        }

    7. delete Item  -    http://[hostname:port]/inventory/v1/stationary_item/[id]    (Method - DELETE)
   
    8. student signup -   http://[hostname:port]/inventory/v1/student       (Method - POST)

        {
            "studentName":"Prashant Shekhar",
            "studentEmail":"prashant@gmail.com",
            "studentPassword":"123456",
            "dateOfBirth":"2001-05-23"
        }

    9.  student login -   http://[hostname:port]/inventory/v1/student/login           (Method - POST)

        { 
            "studentEmail":"prashant@gmail.com",
            "studentPassword":"123456"
        }

### Transaction

    1. create Transaction   - http://[hostname:port]/inventory/v1/transaction/[studentId]        (Method - POST)
    
        {
            "stationaryItemId":"1",
            "withdrawnQuantity":35,
            "returnDate":"2023-07-20",
            "returned":"false"
        }  

    2. findOneTransaction    -  http://[hostname:port]/inventory/v1/transaction/[transactionId]              (Method - GET)
    3. findAllTransaction    -  http://[hostname:port]/inventory/v1/transaction                            (Method - GET)
    4. findAllByStudentId    -  http://[hostname:port]/inventory/v1/transaction/all/by_student_id?id=3        (Method - GET)
    5. findAllByItemId       -  http://[hostname:port]/inventory/v1/transaction/all/by_item_id?id=1           (Method - GET)
    6. UpdateOneTransaction  -  http://[hostname:port]/inventory/v1/transaction/[transactionId]           (Method - PATCH)

        {
            "returned": true,   //(optional)
            "returnDate":"2023-07-20"   //(optional)
        }





![](./ER_DIAGRAM.png)