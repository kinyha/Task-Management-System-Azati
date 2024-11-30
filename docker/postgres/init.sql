-- Create task_manager_test database if it doesn't exist
SELECT 'CREATE DATABASE task_manager_test'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'task_manager_test')\gexec