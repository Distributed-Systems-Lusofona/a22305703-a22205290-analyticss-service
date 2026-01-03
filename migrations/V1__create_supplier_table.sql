CREATE TABLE IF NOT EXISTS supplier (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20),
    contact_person VARCHAR(50),
    tax_number VARCHAR(20) UNIQUE,
    country VARCHAR(50),
    city VARCHAR(50),
    established_date VARCHAR(10),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
    );

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_supplier_email ON supplier(email);
CREATE INDEX IF NOT EXISTS idx_supplier_tax_number ON supplier(tax_number);
CREATE INDEX IF NOT EXISTS idx_supplier_active ON supplier(is_active);
CREATE INDEX IF NOT EXISTS idx_supplier_country ON supplier(country);