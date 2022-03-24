//
//  LoadState+Boolean.swift
//  ios
//
//  Created by Harry on 2022/03/24.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import common_client

extension LoadState {
    
    var isLoading : Bool {
        LoadStateKt.isLoading(self)
    }
    
    var isFailed : Bool {
        LoadStateKt.isFailed(self)
    }
    
}
