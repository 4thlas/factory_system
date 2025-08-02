UPDATE facilities
SET total_revenue = 0
WHERE total_revenue IS NULL;

alter table facilities
    alter column total_revenue set default 0;