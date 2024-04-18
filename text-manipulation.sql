SELECT account_number, contact_person, account_type, billing_group
FROM accounts
WHERE account_type='<type>'
AND billing_group='<bill_group>'