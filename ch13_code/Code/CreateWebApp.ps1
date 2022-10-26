# Log in to Azure
az login

# Select Azure Subscription
az account set -s "{subscription}" 

# Create Resource Group
az group create --location eastus --name {name}

# Get all SKUs available for App Service Plan and other details for creating WebApp
az appservice plan create --help

# Create App Service Plan
az appservice plan create --resource-group {resourcegroup-name} --name {name} --sku S1 --location eastus

# Create WebApp
az webapp create --name {name} --resource-group {resourcegroup-name} --plan {plan-name} --runtime "java:11:Java SE:11"

