ACCOUNT_TYPE='residential'
BILLING_GROUP='baltimore'

# -i option to edit the file in-place
# "s:<type>:string:g" subtitution command, the 'g' on the last command it replaces all occurrences in each line
# I used ':' instead of '/' because pipeline interpreter errors(based on my experience)
sed -i "s:<type>:$ACCOUNT_TYPE:g" text-manipulation.sql
sed -i "s:<bill_group>:$BILLING_GROUP:g" text-manipulation.sql