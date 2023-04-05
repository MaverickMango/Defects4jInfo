#!/bin/bash
Base_Dir="/home/liu/Desktop/"
mode=$1
result="VBAPRResult"

cat "${Base_Dir}projIDs_2" | while read line
do
	proj=$(echo $line | cut -f 1 -d ":")
	bids=$(echo $line | cut -f 2 -d ":" | sed 's/,/ /g')
	echo "----------start $proj----------"
	cd ${Base_Dir}${result}
	mkdir $proj
	
	
	for i in $bids
	do	
		echo "----------$proj $i checkout----------"
		/home/liu/Desktop/defects4j/framework/bin/defects4j checkout -p $proj -v $i\b -w ./Defects4jProjs/$proj\_$i

		cd ./Defects4jProjs/$proj\_$i
		echo "----------$proj $i compile----------"
		/home/liu/Desktop/defects4j/framework/bin/defects4j compile
	
		tests=($(find . -maxdepth 2 -name *test* -type d | sed 's/\.//g' | sed 's/$/&\//g'))

		cd ${Base_Dir}astor
		echo "----------$proj $i astor run----------"
		if [[ "$proj" = "Lang" ]]
		then
			java -cp target/astor-2.0.0-jar-with-dependencies.jar fr.inria.main.evolution.VBAPRMain -customengine fr.inria.astor.approaches.jgenprog.extension.VBAPR -srcjavafolder /src/main/java/ -srctestfolder ${tests[1]}  -binjavafolder /target/classes/ -bintestfolder ${tests[0]} -location ${Base_Dir}${result}/Defects4jProjs/$proj\_$i/ -dependencies ./../${result}/Defects4jProjs/$proj\_$i/lib -faultlocalization fr.inria.astor.core.faultlocalization.gzoltar.GZoltarFaultLocalizationWithGT -operatorspace fr.inria.astor.approaches.jgenprog.extension.VBAPRSpace -targetelementprocessor fr.inria.astor.core.manipulation.filters.SingleExpressionFixSpaceProcessor -opselectionstrategy fr.inria.astor.core.solutionsearch.spaces.operators.GTBRepairOperatorSpace -ingredientstrategy fr.inria.astor.core.solutionsearch.spaces.ingredients.ingredientSearch.GTBSelectionIngredientSearchStrategy >> ConsoleOut_$proj\_$i 2>&1
		fi
		if [[ "$proj" = "Time" ]]
		then
			java -cp target/astor-2.0.0-jar-with-dependencies.jar fr.inria.main.evolution.VBAPRMain -customengine fr.inria.astor.approaches.jgenprog.extension.VBAPR -srcjavafolder /src/main/java/ -srctestfolder /src/test/  -binjavafolder /build/classes/ -bintestfolder /build/tests/ -location ${Base_Dir}${result}/Defects4jProjs/$proj\_$i/ -dependencies ./../${result}/Defects4jProjs/$proj\_$i/lib -faultlocalization fr.inria.astor.core.faultlocalization.gzoltar.GZoltarFaultLocalizationWithGT -operatorspace fr.inria.astor.approaches.jgenprog.extension.VBAPRSpace -targetelementprocessor fr.inria.astor.core.manipulation.filters.SingleExpressionFixSpaceProcessor -opselectionstrategy fr.inria.astor.core.solutionsearch.spaces.operators.GTBRepairOperatorSpace -ingredientstrategy fr.inria.astor.core.solutionsearch.spaces.ingredients.ingredientSearch.GTBSelectionIngredientSearchStrategy >> ConsoleOut_$proj\_$i 2>&1
		fi
		if [[ "$proj" = "Math" ]]
		then
			java -cp target/astor-2.0.0-jar-with-dependencies.jar fr.inria.main.evolution.VBAPRMain -customengine fr.inria.astor.approaches.jgenprog.extension.VBAPR -srcjavafolder /src/main/java/ -srctestfolder /src/test/  -binjavafolder /target/classes/ -bintestfolder /target/test-classes/ -location ${Base_Dir}${result}/Defects4jProjs/$proj\_$i/ -dependencies ./../${result}/Defects4jProjs/$proj\_$i/lib -faultlocalization fr.inria.astor.core.faultlocalization.gzoltar.GZoltarFaultLocalizationWithGT -operatorspace fr.inria.astor.approaches.jgenprog.extension.VBAPRSpace -targetelementprocessor fr.inria.astor.core.manipulation.filters.SingleExpressionFixSpaceProcessor -opselectionstrategy fr.inria.astor.core.solutionsearch.spaces.operators.GTBRepairOperatorSpace -ingredientstrategy fr.inria.astor.core.solutionsearch.spaces.ingredients.ingredientSearch.GTBSelectionIngredientSearchStrategy >> ConsoleOut_$proj\_$i 2>&1
		fi
		if [[ "$proj" = "Chart" ]]
		then
			java -cp target/astor-2.0.0-jar-with-dependencies.jar fr.inria.main.evolution.VBAPRMain -customengine fr.inria.astor.approaches.jgenprog.extension.VBAPR -srcjavafolder /source/ -srctestfolder /tests/  -binjavafolder /build/ -bintestfolder  /build-tests/ -location ${Base_Dir}${result}/Defects4jProjs/$proj\_$i/ -dependencies ./../${result}/Defects4jProjs/$proj\_$i/lib -faultlocalization fr.inria.astor.core.faultlocalization.gzoltar.GZoltarFaultLocalizationWithGT -operatorspace fr.inria.astor.approaches.jgenprog.extension.VBAPRSpace -targetelementprocessor fr.inria.astor.core.manipulation.filters.SingleExpressionFixSpaceProcessor -opselectionstrategy fr.inria.astor.core.solutionsearch.spaces.operators.GTBRepairOperatorSpace -ingredientstrategy fr.inria.astor.core.solutionsearch.spaces.ingredients.ingredientSearch.GTBSelectionIngredientSearchStrategy >> ConsoleOut_$proj\_$i 2>&1
		fi
		cd ${Base_Dir}astor
		mv ./ConsoleOut_$proj\_$i ${Base_Dir}astor/output_astor/VBAPRMain-$proj\_$i
		echo "----------$proj $i astor done----------"
		cd ${Base_Dir}${result}/$proj
		mv ${Base_Dir}astor/output_astor/VBAPRMain-$proj\_$i ./$proj\_$i
		cd $proj\_$i
		rm -rf bin
		cd src
		rm -rf default*
		dirs=$(ls -d variant*)
		for dirName in $dirs
		do
			if [[ "$dirName" = variant* ]]
			then
				cd $dirName
				rm -rf org
				cd ..
				continue;
			fi
			rm -rf $dirName
		done
		cd ${Base_Dir}${result}
		rm -rf Defects4jProjs/$proj\_$i
	done
done

