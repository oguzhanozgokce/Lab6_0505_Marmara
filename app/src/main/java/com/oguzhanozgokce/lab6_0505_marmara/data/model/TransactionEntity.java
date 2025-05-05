    package com.oguzhanozgokce.lab6_0505_marmara.data.model;

    import androidx.room.Entity;
    import androidx.room.PrimaryKey;
    import androidx.room.ColumnInfo;

    @Entity(tableName = "transactions")
    public class TransactionEntity {

        @PrimaryKey(autoGenerate = true)
        public int id;

        @ColumnInfo(name = "person_name")
        public String personName;

        @ColumnInfo(name = "transaction_type")
        public String transactionType;

        @ColumnInfo(name = "amount")
        public Double amount;

        @ColumnInfo(name = "date")
        public String date;

        public TransactionEntity() {}

        public TransactionEntity(int id, String personName, String transactionType, Double amount, String date) {
            this.id = id;
            this.personName = personName;
            this.transactionType = transactionType;
            this.amount = amount;
            this.date = date;
        }
    }
