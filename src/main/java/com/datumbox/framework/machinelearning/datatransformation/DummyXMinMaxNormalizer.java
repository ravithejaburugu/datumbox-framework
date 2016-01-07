/**
 * Copyright (C) 2013-2016 Vasilis Vryniotis <bbriniotis@datumbox.com>
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
package com.datumbox.framework.machinelearning.datatransformation;

import com.datumbox.common.dataobjects.Dataframe;
import com.datumbox.common.persistentstorage.interfaces.DatabaseConfiguration;
import com.datumbox.framework.machinelearning.common.bases.datatransformation.BaseDummyMinMaxTransformer;
import java.util.Map;

/**
 * Dummy X MinMax Normalizer: Transforms the X vector to the 0.0-1.0 scale and 
 * builds dummy variables when non-numerics are detected. No normalization is 
 * performed on Y values. This normalizer can be used for Logistic Regression.
 * 
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class DummyXMinMaxNormalizer extends BaseDummyMinMaxTransformer {
    
    /**
     * Public constructor of the algorithm.
     * 
     * @param dbName
     * @param dbConf 
     */
    public DummyXMinMaxNormalizer(String dbName, DatabaseConfiguration dbConf) {
        super(dbName, dbConf);
    }
    
    @Override
    protected void _fit(Dataframe data) {
        Map<Object, Double> minColumnValues = kb().getModelParameters().getMinColumnValues();
        Map<Object, Double> maxColumnValues = kb().getModelParameters().getMaxColumnValues();
        BaseDummyMinMaxTransformer.fitX(data, minColumnValues, maxColumnValues);
        BaseDummyMinMaxTransformer.fitDummy(data, kb().getModelParameters().getReferenceLevels());
    }
    
    @Override
    protected void _convert(Dataframe data) {
        BaseDummyMinMaxTransformer.transformDummy(data, kb().getModelParameters().getReferenceLevels());
    }
    
    @Override
    protected void _normalize(Dataframe data) {
        Map<Object, Double> minColumnValues = kb().getModelParameters().getMinColumnValues();
        Map<Object, Double> maxColumnValues = kb().getModelParameters().getMaxColumnValues();

        BaseDummyMinMaxTransformer.normalizeX(data, minColumnValues, maxColumnValues);
    }

    @Override
    protected void _denormalize(Dataframe data) {
        Map<Object, Double> minColumnValues = kb().getModelParameters().getMinColumnValues();
        Map<Object, Double> maxColumnValues = kb().getModelParameters().getMaxColumnValues();

        BaseDummyMinMaxTransformer.denormalizeX(data, minColumnValues, maxColumnValues);
    }
}
