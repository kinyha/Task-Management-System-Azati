-- Insert test users
INSERT INTO users (id, username, email, password, role)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'test-user', 'test@test.com', 'password', 'REGULAR'),
    ('22222222-2222-2222-2222-222222222222', 'test-manager', 'manager@test.com', 'password', 'MANAGER')
ON CONFLICT (id) DO NOTHING;

-- Insert test tasks
INSERT INTO tasks (id, title, status, priority, created_by, assigned_user_id, created_at)
VALUES
    ('33333333-3333-3333-3333-333333333333', 'Test Task 1', 'TODO', 'MEDIUM',
     '11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', CURRENT_TIMESTAMP),
    ('44444444-4444-4444-4444-444444444444', 'Test Task 2', 'TODO', 'HIGH',
     '11111111-1111-1111-1111-111111111111', null, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;