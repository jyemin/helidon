/*
 * Copyright (c) 2019, 2023 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.helidon.dbclient.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.helidon.dbclient.DbClientContext;
import io.helidon.dbclient.DbTransaction;

public class MongoDbTransaction extends MongoDbExecute implements DbTransaction {

    public MongoDbTransaction(DbClientContext ctx, MongoClient client, MongoDatabase db) {
        super(ctx, db, client.startSession());
        session().startTransaction();
    }

    @Override
    public void commit() {
        try {
            session().commitTransaction();
        } finally {
            session().close();
        }
    }

    @Override
    public void rollback() {
        try {
            session().abortTransaction();
        } finally {
            session().close();
        }
    }
}
