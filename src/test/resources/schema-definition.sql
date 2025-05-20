CREATE TABLE client (
                        id UUID PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

-- Accounts table (savings and checking)
CREATE TABLE account (
                         id UUID PRIMARY KEY,
                         client_id UUID REFERENCES client(id) ON DELETE CASCADE,
                         type VARCHAR(20) NOT NULL CHECK (type IN ('SAVINGS', 'CHECKING')),
                         balance NUMERIC(15, 2) NOT NULL DEFAULT 0
);

-- Transactions table
CREATE TABLE transaction (
                             id UUID PRIMARY KEY,
                             type VARCHAR(20) NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),
                             amount NUMERIC(15, 2) NOT NULL,
                             timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                             account_origin UUID REFERENCES account(id),
                             account_destination UUID REFERENCES account(id),

                             CONSTRAINT chk_transfer_type CHECK (
                                 (type = 'TRANSFER' AND account_origin IS NOT NULL AND account_destination IS NOT NULL)
                                     OR
                                 (type IN ('DEPOSIT', 'WITHDRAWAL') AND account_origin IS NOT NULL AND account_destination IS NULL)
                                 )
);