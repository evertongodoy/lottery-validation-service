db = db.getSiblingDB('lottery_validation');

db.createCollection('users');

db.users.createIndex({ "subject": 1 }, { unique: true });
db.users.createIndex({ "cellphone": 1 });

print('Database initialized successfully');
