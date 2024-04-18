ACCOUNT_TYPE='residential'
BILLING_GROUP='baltimore'

# Open the sql file and replace the <type> and <bill_group> to new value
with open('text-manipulation.sql', 'r') as file:
    sql = file.read()
    # repalce target string to new value
    sql = sql.replace("<type>", ACCOUNT_TYPE)
    sql = sql.replace("<bill_group>", BILLING_GROUP)
    print(sql)

# Overwrite the existing sql to updated sql 
with open('text-manipulation.sql', 'w') as file:
    file.write(sql)