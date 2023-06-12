CREATE TABLE IF NOT EXISTS tool(
    id uuid DEFAULT gen_random_uuid() primary key,
    tool_code VARCHAR(255),
    tool_type VARCHAR(255),
    brand VARCHAR(255)
);

INSERT INTO tool (tool_code, tool_type, brand) VALUES
    ('CHNS', 'Chainsaw', 'Stihl'),
    ('LADW', 'Ladder', 'Werner'),
    ('JAKD', 'Jackhammer', 'DeWalt'),
    ('JAKR', 'Jackhammer', 'Rigid')
;

CREATE TABLE IF NOT EXISTS rental_fee(
    id uuid DEFAULT gen_random_uuid() primary key,
    tool_type VARCHAR(255),
    daily_charge DECIMAL(10, 2),
    weekday_charge boolean,
    weekend_charge boolean,
    holiday_charge boolean
);

INSERT INTO rental_fee (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES
    ('Ladder', 1.99, true, true, false),
    ('Chainsaw', 1.46, true, false, true),
    ('Jackhammer', 2.99, true, false, false)
;