#!/usr/bin/env bash
while getopts a:n:ns:u:d: flag
do
    case "${flag}" in
        a) author=${OPTARG};;
        n) name=${OPTARG};;
        ns) name_snake=${OPTARG};;
        u) urlname=${OPTARG};;
        d) description=${OPTARG};;
    esac
done

echo "Author: $author";
echo "Project Name: $name_snake";
echo "Project Name Snake: $name";
echo "Project URL name: $urlname";
echo "Description: $description";

echo "Renaming project..."

original_author="SettingDust"
original_name="FabricKotlinTemplate"
original_name_snake="fabric-kotlin-template"
original_urlname="https://github.com/SettingDust/FabricKotlinTemplate"
original_description="An example mod"
original_group="settingdust.template"
group="$($author | tr ' ' '' | tr '[:upper:]' '[:lower:]').$($name | tr ' ' '' | tr '[:upper:]' '[:lower:]')"
group_path="$($group | tr '.' '/')"
# for filename in $(find . -name "*.*")
for filename in $(git ls-files)
do
    sed -i "s/$original_author/$author/g" "$filename"
    sed -i "s/$original_name/$name/g" "$filename"
    sed -i "s/$original_name_snake/$name_snake/g" "$filename"
    sed -i "s/$original_urlname/$urlname/g" "$filename"
    sed -i "s/$original_description/$description/g" "$filename"
    sed -i "s/$original_group/$group/g" "$filename"
    echo "Renamed $filename"
done

mv src/client/java/settingdust/template "src/client/java/$group_path"
mv src/client/kotlin/settingdust/template "src/client/kotlin/$group_path"
mv src/client/resources/assets/fabric-kotlin-template "src/client/resources/assets/$name_snake"
mv src/client/resources/fabric-kotlin-template.client.mixins.json "src/client/resources/$name_snake.client.mixins.json"
mv src/main/java/settingdust/template "src/main/java/$group_path"
mv src/main/kotlin/settingdust/template "src/main/kotlin/$group_path"
mv src/main/resources/fabric-kotlin-template.mixins.json "src/main/resources/$name_snake.mixins.json"
# This command runs only once on GHA!
#rm -rf .github/template.yml
