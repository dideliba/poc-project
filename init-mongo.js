var config = {
    "_id": "rs0",
    "version": 1,
    "members": [
        {
            "_id": 1,
            "host": "localhost:27017",
            "priority": 1
        }
    ]
};
rs.initiate(config)

db = db.getSiblingDB("userDB");
db.createUser(
    {
        user: "user1",
        pwd: "user1",
        roles: [
            {
                role: "readWrite",
                db: "userDB"
            }
        ]
    }
);