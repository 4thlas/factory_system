alter table facilities
    change monthly_revenue total_revenue bigint null;

alter table production_lines
    change produced_per_month total_produced bigint null;