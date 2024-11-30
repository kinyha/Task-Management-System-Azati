-- Clean up
TRUNCATE TABLE tasks CASCADE;
TRUNCATE TABLE users CASCADE;

-- Test Users
INSERT INTO users (id, username, email, password, role)
VALUES ('11111111-1111-1111-1111-111111111111', 'john.doe', 'john@company.com', 'password', 'REGULAR'),
       ('22222222-2222-2222-2222-222222222222', 'jane.smith', 'jane@company.com', 'password', 'MANAGER'),
       ('33333333-3333-3333-3333-333333333333', 'bob.wilson', 'bob@company.com', 'password', 'REGULAR'),
       ('44444444-4444-4444-4444-444444444444', 'alice.manager', 'alice@company.com', 'password', 'MANAGER');

-- Tasks
-- Overdue IN_PROGRESS tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('a3333333-3333-3333-3333-333333333333', 'Implement User API', 'REST API for user management',
        'IN_PROGRESS', 'HIGH', '22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111',
        (CURRENT_DATE - INTERVAL '20 days'), (CURRENT_DATE - INTERVAL '1 day'));

-- Future deadline tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('b1111111-1111-1111-1111-111111111111', 'Write Documentation', 'API documentation',
        'TODO', 'LOW', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333',
        (CURRENT_DATE - INTERVAL '5 days'), (CURRENT_DATE + INTERVAL '10 days'));

INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('b2222222-2222-2222-2222-222222222222', 'Implement Logging', 'Add proper logging',
        'IN_PROGRESS', 'MEDIUM', '22222222-2222-2222-2222-222222222222', '44444444-4444-4444-4444-444444444444',
        (CURRENT_DATE - INTERVAL '3 days'), (CURRENT_DATE + INTERVAL '5 days'));

-- No deadline tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('c1111111-1111-1111-1111-111111111111', 'Code Review', 'Review pull requests',
        'TODO', 'LOW', '33333333-3333-3333-3333-333333333333', null,
        (CURRENT_DATE - INTERVAL '2 days'), null);

-- Recently completed tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('d1111111-1111-1111-1111-111111111111', 'Setup CI/CD', 'Configure Jenkins pipeline',
        'DONE', 'HIGH', '22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111',
        (CURRENT_DATE - INTERVAL '30 days'), (CURRENT_DATE - INTERVAL '15 days'));
-- Blocked tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('e1111111-1111-1111-1111-111111111111', 'Database Migration', 'Migrate to PostgreSQL',
        'BLOCKED', 'CRITICAL', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333',
        (CURRENT_DATE - INTERVAL '25 days'), (CURRENT_DATE - INTERVAL '10 days'));

-- Long-term planning tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('f1111111-1111-1111-1111-111111111111', 'Performance Optimization', 'Optimize API endpoints',
        'TODO', 'MEDIUM', '44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111',
        CURRENT_DATE, (CURRENT_DATE + INTERVAL '30 days'));

-- Priority-based tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES ('f2211111-1111-1111-1111-111111111111', 'Security Audit', 'Perform security review',
        'TODO', 'CRITICAL', '44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111',
        CURRENT_DATE, (CURRENT_DATE + INTERVAL '2 days'));

-- Priority-based tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a7111111-1111-1111-1111-111111111111', 'Security Audit', 'Perform security review',
     'TODO', 'CRITICAL', '22222222-2222-2222-2222-222222222222', '44444444-4444-4444-4444-444444444444',
     (CURRENT_DATE - INTERVAL '1 day'), (CURRENT_DATE + INTERVAL '2 days'));

-- Maintenance tasks
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8111111-1111-1111-1111-111111111111', 'Backup Configuration', 'Setup backup strategy',
     'IN_PROGRESS', 'HIGH', '33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222',
     (CURRENT_DATE - INTERVAL '7 days'), (CURRENT_DATE + INTERVAL '7 days'));

-- Frontend Integration Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8222222-2222-2222-2222-222222222222', 'Fix CSS Layout Issues', 'Mobile responsive design bugs',
     'TODO', 'MEDIUM', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333',
     (CURRENT_DATE - INTERVAL '3 days'), (CURRENT_DATE - INTERVAL '1 day'));

-- DevOps Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8333333-3333-3333-3333-333333333333', 'Docker Image Optimization', 'Reduce image size by 50%',
     'IN_PROGRESS', 'HIGH', '22222222-2222-2222-2222-222222222222', null,
     (CURRENT_DATE - INTERVAL '5 days'), (CURRENT_DATE - INTERVAL '2 days'));

-- Data Migration Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8444444-4444-4444-4444-444444444444', 'Legacy Data Migration', 'Move data from MySQL to PostgreSQL',
     'BLOCKED', 'CRITICAL', '22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111',
     (CURRENT_DATE - INTERVAL '10 days'), (CURRENT_DATE - INTERVAL '3 days'));

-- Testing Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8555555-5555-5555-5555-555555555555', 'Write Integration Tests', 'Add tests for user service',
     'TODO', 'MEDIUM', '33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444',
     (CURRENT_DATE - INTERVAL '4 days'), (CURRENT_DATE + INTERVAL '3 days'));

-- Monitoring Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8666666-6666-6666-6666-666666666666', 'Setup Prometheus Metrics', 'Add application metrics',
     'IN_PROGRESS', 'HIGH', '44444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222',
     (CURRENT_DATE - INTERVAL '6 days'), (CURRENT_DATE - INTERVAL '1 day'));

-- API Development
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8777777-7777-7777-7777-777777777777', 'Implement OAuth2', 'Add social login support',
     'TODO', 'CRITICAL', '22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111',
     (CURRENT_DATE - INTERVAL '8 days'), (CURRENT_DATE - INTERVAL '2 days'));

-- Bug Fix
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8888888-8888-8888-8888-888888888888', 'Fix Memory Leak', 'Heap dump analysis required',
     'IN_PROGRESS', 'CRITICAL', '11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222',
     (CURRENT_DATE - INTERVAL '7 days'), (CURRENT_DATE - INTERVAL '1 day'));

-- Documentation
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a8999999-9999-9999-9999-999999999999', 'Update API Docs', 'Swagger documentation update',
     'TODO', 'LOW', '33333333-3333-3333-3333-333333333333', null,
     (CURRENT_DATE - INTERVAL '5 days'), (CURRENT_DATE + INTERVAL '5 days'));

-- Performance Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a9111111-1111-1111-1111-111111111111', 'Query Optimization', 'Optimize slow SQL queries',
     'IN_PROGRESS', 'HIGH', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333',
     (CURRENT_DATE - INTERVAL '9 days'), (CURRENT_DATE - INTERVAL '2 days'));

-- Security Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a9222222-2222-2222-2222-222222222222', 'Fix XSS Vulnerability', 'Input sanitization needed',
     'TODO', 'CRITICAL', '44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111',
     (CURRENT_DATE - INTERVAL '4 days'), (CURRENT_DATE - INTERVAL '1 day'));

-- Infrastructure Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a9333333-3333-3333-3333-333333333333', 'Setup Dev Environment', 'Docker compose for local dev',
     'DONE', 'MEDIUM', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333',
     (CURRENT_DATE - INTERVAL '15 days'), (CURRENT_DATE - INTERVAL '5 days'));

-- Configuration Task
INSERT INTO tasks (id, title, description, status, priority, created_by, assigned_user_id, created_at, deadline)
VALUES
    ('a9444444-4444-4444-4444-444444444444', 'Update App Config', 'Externalize configurations',
     'TODO', 'MEDIUM', '11111111-1111-1111-1111-111111111111', '44444444-4444-4444-4444-444444444444',
     (CURRENT_DATE - INTERVAL '6 days'), (CURRENT_DATE - INTERVAL '1 day'));